package org.example.clickstack.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PhotosModel {
    private String name;
    private URL link;
}
