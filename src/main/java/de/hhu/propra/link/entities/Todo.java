package de.hhu.propra.link.entities;

import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    @Id
    private Long id;

    private String username;
    private Long userId;
    private String title;
    private Boolean completed;


}
