package com.interviewrestapi.model;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "{com.interviewrestapi.onlyAlphabeticalCharacters.message}")
    @Size(min = 2, max = 30)
    @CsvBindByName
    private String name;
    @Size(min = 2, max = 30)
    @CsvBindByName
    private String username;
    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",+", writeDelimiter = ",")
    private List<String> interests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
