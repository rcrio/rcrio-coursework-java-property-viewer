import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed.
 *
 *
 * @author Michael Kölling and Josh Murphy
 * @version 1.0
 * @coursework for Ricky Chan K23062540
 */
public class PropertyViewer
{
    private PropertyViewerGUI gui;     // the Graphical User Interface
    private Portfolio portfolio;
    private int propertyNumber = 0;
    private int propertyViewCounter = 1;
    private int propertyPriceTotal;
    private int averagePropertyPrice;
    int size;

    /**
     * Create a PropertyViewer and display its GUI on screen.
     */
    public PropertyViewer()
    {
        gui = new PropertyViewerGUI(this);
        portfolio = new Portfolio("airbnb-london.csv");
        size = portfolio.numberOfProperties();
        showProperty();
        setAveragePropertyPrice();
        gui.refreshStatistics();
    }

    /**
     * Shows the current property and the information regarding the property.
     */
    public void showProperty()
    {
        gui.showID(portfolio.getProperty(propertyNumber));
        gui.showProperty(portfolio.getProperty(propertyNumber));
        gui.showFavourite(portfolio.getProperty(propertyNumber));
    }
    /**
     * View the next property in the portfolio when clicking "Next" on the PropertyViewer GUI.
     */
    public void nextProperty()
    {
        //Lists are 0-indexed so we must do (size-1) to get the correct value.
        if (propertyNumber < (size-1)) {
            propertyNumber++;
        }
        else {
            //Goes to the first entry of the portfolio.
            propertyNumber = 0;
        }
        showProperty();
        propertyViewCounter ++;
        setAveragePropertyPrice();
        gui.refreshStatistics();
    }

    /**
     * Views the previous property in the portfolio when clicking "Previous" on the PropertyViewer GUI.
     */
    public void previousProperty()
    {
        if (propertyNumber > 0) {
            propertyNumber --;
        }
        else {
            //Goes to the last entry of the portfolio.
            propertyNumber = (size-1);
        }
        showProperty();
        propertyViewCounter ++;
        setAveragePropertyPrice();
        gui.refreshStatistics();
    }

    /**
     * Favourites a property that is currently viewed on the GUI and updates it as well on the GUI and in the code.
     */
    public void toggleFavourite()
    {
        portfolio.getProperty(propertyNumber).toggleFavourite();
        showProperty();
    }


    //----- methods for challenge tasks -----

    /**
     * This method opens the system's default internet browser
     * The Google maps page should show the current properties location on the map.
     */
    public void viewMap() throws Exception
    {
        double latitude = portfolio.getProperty(propertyNumber).getLatitude();
        double longitude = portfolio.getProperty(propertyNumber).getLongitude();

        URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri);
    }

    /**
     * Returns the properties viewed currently.
     */
    public int getNumberOfPropertiesViewed()
    {
        return propertyViewCounter;
    }

    /**
     * Calculates the average price of the properties viewed so far.
     */
    public void setAveragePropertyPrice()
    {
        propertyPriceTotal += portfolio.getProperty(propertyNumber).getPrice();
        averagePropertyPrice = propertyPriceTotal / propertyViewCounter;
    }

    /**
     * Returns the average price of the properties viewed so far.
     */
    public int averagePropertyPrice()
    {
        return averagePropertyPrice;
    }

}
