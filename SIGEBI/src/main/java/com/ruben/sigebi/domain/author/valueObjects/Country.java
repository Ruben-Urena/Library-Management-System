package com.ruben.sigebi.domain.author.valueObjects;
import java.util.*;

public record Country(String country){

    public Country{
        if (country == null){
            throw new IllegalArgumentException("Country argument cannot be null: "+ country);
        }
        List<String> countriesHashSet = new ArrayList<>(Arrays.asList(Locale.getISOCountries()))
                .stream()
                .map(a -> {
                     var x = new Locale.Builder()
                            .setLanguage("en")
                            .setRegion(a)
                            .build();
                     return x.getDisplayCountry().toLowerCase();
                } )
                .toList();
        country = country.toLowerCase().trim();

        if ( !(countriesHashSet.contains(country))){
            throw new IllegalArgumentException("This country code does not exist: "+ country);
        }else {
            var arraysOfText = country.split(" ");
            StringBuilder countryCodeBuilder = new StringBuilder();
            for (var gasp:arraysOfText){

                var correctString = gasp;
                if (!(gasp.equals("&"))){
                    correctString = gasp.substring(0,1).toUpperCase() + gasp.substring(1);
                }
                countryCodeBuilder.append(correctString);
                countryCodeBuilder.append(" ");
            }
            country = countryCodeBuilder.toString().trim();
        }
    }


    @Override
    public String toString() {
        return country;
    }
}
