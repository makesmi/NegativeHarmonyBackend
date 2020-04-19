
package harmony.domain;

import java.io.Serializable;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Table
@IdClass(Song.Identifier.class)
public class Song{
@Column(length = 64)
    @Id
    private String userId;
    @Column(length = 64)
    @Id
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String code;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class Identifier implements Serializable{
        String userId;
        String name;

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Identifier)){ return false; }
            Identifier id = (Identifier)obj;
            return userId.equals(id.userId) && name.equals(id.name);
        }

        @Override
        public int hashCode() {
            return userId.hashCode() + name.hashCode();
        }
    }
}
