package org.acheh.demo.recipe.manager.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Recipe {

    @Id
    @Column(name = "id")
    private Long id;

}
