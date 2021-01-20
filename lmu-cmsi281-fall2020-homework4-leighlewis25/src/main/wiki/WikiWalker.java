package main.wiki;

import java.util.*;

public class WikiWalker {

    private HashMap<String, HashMap<String, Integer>> wikiMap = new HashMap<>();

    public WikiWalker() {
        this.wikiMap = new HashMap<String, HashMap<String, Integer>>();
    }

    /**
     * Adds an article with the given name to the site map and associates the
     * given linked articles found on the page. Duplicate linksMap in that list are
     * ignored, as should an article's linksMap to itself.
     * 
     * @param articleName
     *            The name of the page's article
     * @param articlelinksMap
     *            List of names for those articles linked on the page
     */
    public void addArticle(String articleName, List<String> articlelinksMap) {
        wikiMap.put(articleName, new HashMap<String, Integer>());
        for (int i = 0; i < articlelinksMap.size(); i++) {
            if (!articlelinksMap.get(i).equals(articleName) && !wikiMap.get(articleName).containsKey(articlelinksMap.get(i))){
                wikiMap.get(articleName).put(articlelinksMap.get(i), 0);
            }
        }
    }

    /**
     * Determines whether or not, based on the added articles with their linksMap,
     * there is *some* sequence of linksMap that could be followed to take the user
     * from the source article to the destination.
     * 
     * @param src
     *            The beginning article of the possible path
     * @param dest
     *            The end article along a possible path
     * @return boolean representing whether or not that path exists
     */
    public boolean hasPath(String src, String dest) {
        List<String> trackVisits = new ArrayList<>();
        return this.hasPath(src, dest, trackVisits);
    }

    /**
     * Increments the click counts of each link along some trajectory. For
     * instance, a trajectory of ["A", "B", "C"] will increment the click count
     * of the "B" link on the "A" page, and the count of the "C" link on the "B"
     * page. Assume that all given trajectories are valid, meaning that a link
     * exists from page i to i+1 for each index i
     * 
     * @param traj
     *            A sequence of a user's page clicks; must be at least 2 article
     *            names in length
     */
    public void logTrajectory(List<String> traj) {
        if (traj.size() < 2) {
            throw new IllegalArgumentException();
        }      
        String click;
        
        for (int i= 0, j = 1; i < traj.size() && j < traj.size(); i++, j++) {
            click = traj.get(i);
            wikiMap.get(click).replace(traj.get(j), wikiMap.get(click).get(traj.get(j)) + 1);
        }
    }

    /**
     * Returns the number of clickthroughs recorded from the src article to the
     * destination article. If the destination article is not a link directly
     * reachable from the src, returns -1.
     * 
     * @param src
     *            The article on which the clickthrough occurs.
     * @param dest
     *            The article requested by the clickthrough.
     * @throws IllegalArgumentException
     *             if src isn't in site map
     * @return The number of times the destination has been requested from the
     *         source.
     */
    public int clickthroughs(String src, String dest) {
        if (!wikiMap.containsKey(src)) {
            throw new IllegalArgumentException();
        }

        if (!wikiMap.get(src).containsKey(dest)) {
            return -1;
        } else {
            return wikiMap.get(src).get(dest);
        }


    }

    /**
     * Based on the pattern of clickthrough trajectories recorded by this
     * WikiWalker, returns the most likely trajectory of k clickthroughs
     * starting at (but not including in the output) the given src article.
     * Duplicates and cycles are possible outputs along a most likely trajectory. In
     * the event of a tie in max clickthrough "weight," this method will choose
     * the link earliest in the ascending alphabetic order of those tied.
     * 
     * @param src
     *            The starting article of the trajectory (which will not be
     *            included in the output)
     * @param k
     *            The maximum length of the desired trajectory (though may be
     *            shorter in the case that the trajectory ends with a terminal
     *            article).
     * @return A List containing the ordered article names of the most likely
     *         trajectory starting at src.
     */
    public List<String> mostLikelyTrajectory(String src, int k) {
        List<String> traj = new ArrayList<>();
        int largNumClicks = 0;
        return mostLikelyTrajectory(src, k, largNumClicks, traj);
    }        


    // Private Helper Methods Below
    // -------------------------------------------------------------------------

    /**
     * Recursive helper method to determine whether or not, based on the 
     * added articles with their linksMap, there is *some* sequence of 
     * linksMap that could be followed to take the user
     * from the source article to the destination.
     * @param src The source article from which the user started
     * @param dest The destination that the user would like to get to
     * @param trackVisits A List tracking linksMap that have already been visited by the user
     * @return true if there is a sequence of linksMap that could be followed from 
     *          the src to the dest, false otherwise
     */
    private boolean hasPath(String src, String dest, List<String> trackVisits) {
        if (src.equals(dest)) {
            return true;
        }

        if (trackVisits.contains(src)) {
            return false;
        }
        trackVisits.add(src);

        if (wikiMap.containsKey(src)) {
            for (Map.Entry<String, Integer> linksMap : wikiMap.get(src).entrySet()) {
                if (linksMap.getKey().equals(dest)) {
                    return true;
                }
                if (hasPath(linksMap.getKey(), dest, trackVisits)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Recursive helper method that, based on the pattern of clickthrough trajectories recorded 
     * by this WikiWalker, returns the most likely trajectory of k clickthroughs
     * starting at (but not including in the output) the given src article.
     * Duplicates and cycles are possible outputs along a most likely trajectory. In
     * the event of a tie in max clickthrough "weight," this method will choose
     * the link earliest in the ascending alphabetic order of those tied.
     * @param src The starting article of the trajectory (which will not be
     *            included in the output)
     * @param k The maximum length of the desired trajectory (though may be
     *            shorter in the case that the trajectory ends with a terminal
     *            article).
     * @param largNumClicks The largest number of clicks of the links contained in the src
     * @param traj A list containing the most likely trajectory of k clickthroughs
     * @return returns the most likely trajectory of k clickthroughs starting at the given src article
     */
    private List<String> mostLikelyTrajectory(String src, int k, int largNumClicks, List<String> traj) {
        String mostClicked = "";
        if (k <= 0) {
            return traj;
        } 
        
        if (wikiMap.containsKey(src)) {
            for (Map.Entry<String, Integer> linksMap : wikiMap.get(src).entrySet()) {
                if (mostClicked.isEmpty()) {
                    mostClicked = linksMap.getKey();
                    largNumClicks = linksMap.getValue();
                } else if (linksMap.getValue() > largNumClicks) {
                    mostClicked = linksMap.getKey();
                    largNumClicks = linksMap.getValue();
                } else if (linksMap.getValue() == largNumClicks) {
                    mostClicked = (linksMap.getKey().compareToIgnoreCase(mostClicked) > 0) ? mostClicked : linksMap.getKey();
                    largNumClicks = (linksMap.getKey().compareToIgnoreCase(mostClicked) > 0) ? largNumClicks : linksMap.getValue();
                } 
            }
            traj.add(mostClicked);
            mostLikelyTrajectory(mostClicked, k - 1, largNumClicks, traj);
        } else {
            k = 0;
        }
        return traj;
    }
}











