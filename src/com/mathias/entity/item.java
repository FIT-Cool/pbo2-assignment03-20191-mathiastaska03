package com.mathias.entity;
/**
 *
 * @author mathias (1472054)
 */
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


public class item implements Serializable {
    private Integer id;
    private String name;
    private Date Expiredate;
    private category category;

    public item(Integer id, String name, Date expiredate, com.mathias.entity.category category) {
        this.id = id;
        this.name = name;
        Expiredate = expiredate;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpiredate() {
        return Expiredate;
    }

    public void setExpiredate(Date expiredate) {
        Expiredate = expiredate;
    }

    public com.mathias.entity.category getCategory() {
        return category;
    }

    public void setCategory(com.mathias.entity.category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return getName()+getExpiredate();
    }

}
