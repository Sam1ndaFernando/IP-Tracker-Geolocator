package lk.CodePro.functionality;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Subdivision;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeoIp {
    public void ipAddress(String ip, TextArea txtArea) {
        try {
            File db = new File("db/GeoLite2-City.mmdb");
            DatabaseReader dbReader = new DatabaseReader.Builder(db).build();

            InetAddress ipAddress = InetAddress.getByName(ip);

            CityResponse response = dbReader.city(ipAddress);

            Country country = response.getCountry();
            City city = response.getCity();
            Location location = response.getLocation();
            Subdivision subdivision = response.getMostSpecificSubdivision();

            String cityName = city.getName();
            String countryName = country.getName();
            String countryCode = country.getIsoCode();
            String regionName = subdivision.getName();
            String regionCode = subdivision.getIsoCode();
            String postalCode = response.getPostal().getCode();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();


            Locale locale = new Locale("", countryCode);
            String countryCapital = "Unknown";
            String timeZone = location.getTimeZone();
            Currency currency = Currency.getInstance(locale);
            String currencyCode = currency.getCurrencyCode();
            String currencyName = currency.getDisplayName();

            String countryArea = "Unknown";
            String countryPopulation = "Unknown";
            String network = ipAddress.getHostAddress();
            String version = ipAddress instanceof java.net.Inet4Address ? "IPv4" : "IPv6";

            txtArea.setText(
                    "---------------------------" + "\n" +
                            "IP: " + ip + "\n" +
                            "Country: " + countryName + "\n" +
                            "Country Code: " + countryCode + "\n" +
                            "City: " + cityName + "\n" +
                            "Region: " + regionName + "\n" +
                            "Region Code: " + regionCode + "\n" +
                            "Postal Code: " + postalCode + "\n" +
//                            "Capital: " + countryCapital + "\n" +
                            "Timezone: " + timeZone + "\n" +
                            "Currency: " + currencyName + " (" + currencyCode + ")" + "\n" +
//                            "Country Area: " + countryArea + "\n" +
//                            "Country Population: " + countryPopulation + "\n" +
                            "Network: " + network + "\n" +
                            "Version: " + version + "\n" +
                            "Latitude: " + latitude + "\n" +
                            "Longitude: " + longitude + "\n" +
                            "---------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception geoip) {
            txtArea.setText("IP Address not found.");
        } catch (RuntimeException re) {
            txtArea.setText("An error has occurred. Try again later.");
        }
    }


    public boolean checkIp(String ip) {
        String ipv4Pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        Pattern pattern = Pattern.compile(ipv4Pattern);

        Matcher matcher = pattern.matcher(ip);

        return matcher.matches();

    }


    public void getIp(String domain, TextArea txtArea) {
        try {
            URL url = new URL("http://" + domain);
            String ip = InetAddress.getByName(url.getHost()).getHostAddress();
            txtArea.setText(ip);
        } catch (UnknownHostException e) {
            txtArea.setText("Unknown host.");
        } catch (MalformedURLException urlEx) {
            txtArea.setText("Incorrectly formed URL.");
        }
    }
}


