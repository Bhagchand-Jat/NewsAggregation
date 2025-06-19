package com.news_aggregation_system.converter.external;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryInferenceEngine {

    private final Map<String, List<String>> categoryKeywords = new HashMap<>();

    public CategoryInferenceEngine() {
        categoryKeywords.put("Politics", List.of("election", "government", "senate", "president", "minister", "law", "policy", "politician", "parliament", "campaign", "bill", "vote", "diplomacy"));
        categoryKeywords.put("Technology", List.of("AI", "software", "hardware", "app", "startup", "tech", "device", "gadget", "robot", "machine learning", "programming", "cybersecurity", "blockchain", "quantum", "cloud"));
        categoryKeywords.put("Finance", List.of("stock", "market", "investment", "crypto", "interest", "inflation", "bank", "economy", "recession", "budget", "federal", "trading", "bonds", "mutual fund"));
        categoryKeywords.put("Entertainment", List.of("movie", "film", "theatre", "series", "celebrity", "music", "show", "festival", "award", "drama", "concert", "hollywood", "bollywood", "actor", "actress"));
        categoryKeywords.put("Health", List.of("vaccine", "doctor", "hospital", "covid", "mental", "disease", "healthcare", "virus", "cancer", "fitness", "therapy", "nutrition", "medicine", "infection", "pandemic"));
        categoryKeywords.put("Sports", List.of("match", "goal", "tournament", "score", "athlete", "team", "game", "cricket", "football", "olympics", "basketball", "fifa", "medal", "coach", "league"));
        categoryKeywords.put("Science", List.of("research", "discovery", "experiment", "space", "physics", "chemistry", "biology", "nasa", "astronomy", "climate", "quantum", "genetics", "scientist"));
        categoryKeywords.put("Environment", List.of("climate", "global warming", "pollution", "carbon", "eco", "wildlife", "conservation", "recycle", "emissions", "nature", "sustainability", "greenhouse", "deforestation"));
        categoryKeywords.put("Education", List.of("school", "college", "university", "exam", "degree", "student", "teacher", "online learning", "scholarship", "education", "admissions", "curriculum", "syllabus"));
        categoryKeywords.put("Travel", List.of("flight", "hotel", "destination", "tourist", "travel", "trip", "vacation", "guide", "resort", "journey", "explore", "visa", "passport"));
        categoryKeywords.put("Business", List.of("company", "merger", "acquisition", "startup", "business", "enterprise", "industry", "CEO", "corporate", "profit", "revenue", "IPO", "brand", "marketing"));
        categoryKeywords.put("World", List.of("UN", "international", "conflict", "diplomacy", "treaty", "foreign", "global", "embassy", "border", "crisis", "refugee", "war", "summit"));
        categoryKeywords.put("Crime", List.of("murder", "theft", "assault", "arrest", "police", "crime", "court", "trial", "investigation", "fraud", "homicide", "prison", "violence", "criminal"));
        categoryKeywords.put("Weather", List.of("storm", "rain", "flood", "hurricane", "temperature", "forecast", "climate", "cyclone", "snow", "heatwave", "weather", "drought"));
        categoryKeywords.put("Fashion", List.of("style", "trend", "fashion", "model", "designer", "clothing", "runway", "brand", "couture", "wardrobe", "outfit", "accessory", "makeup"));
        categoryKeywords.put("Food", List.of("recipe", "cuisine", "chef", "restaurant", "dining", "meal", "ingredient", "baking", "cook", "dish", "taste", "nutrition", "diet", "flavor"));
        categoryKeywords.put("Real Estate", List.of("property", "real estate", "housing", "mortgage", "rent", "apartment", "broker", "construction", "land", "realtor", "developer", "lease"));
        categoryKeywords.put("Automobile", List.of("car", "vehicle", "automobile", "electric vehicle", "EV", "engine", "bike", "motorcycle", "auto", "tesla", "drive", "transport", "fuel", "SUV"));

    }

    public Set<String> inferCategories(String title, String description, String content) {
        String text = (title + " " + description + " " + content).toLowerCase();

        Set<String> matchedCategories = new HashSet<>();
        for (Map.Entry<String, List<String>> entry : categoryKeywords.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (text.contains(keyword.toLowerCase())) {
                    matchedCategories.add(entry.getKey());
                }
            }
        }
        if(matchedCategories.isEmpty()){
            matchedCategories.add("Other");
        }
        return matchedCategories;
    }
}

