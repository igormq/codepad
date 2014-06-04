package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by igormq on 03/06/14.
 */
@Entity
public class Block extends Model {

    @Id
    @Constraints.Min(10)
    public Long id;

    @Constraints.Required
    public String type;

    @Constraints.Required
    @Lob
    public String content;

    public static Finder<Long,Block> find = new Finder<>(
            Long.class, Block.class
    );
}
