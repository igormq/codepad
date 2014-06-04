package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by igormq on 03/06/14.
 */
@Entity
public class Pad extends Model{
    @Id
    @Constraints.Min(10)
    public Long id;

    @Constraints.Required
    public String title;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Block> blocks;

    public static Finder<Long,Pad> find = new Finder<>(
            Long.class, Pad.class
    );

}
