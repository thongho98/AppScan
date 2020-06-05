package own.thongho.appscan.models;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class History implements Serializable {
    private int id;
    private String category;
    private String content;
    private String date;
    private int img;
    private int like;

    public History() {

    }

    public History(int id, String category, String content, String date, int img, int like) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.date = date;
        this.img = img;
        this.like = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", img=" + img +
                ", like=" + like +
                '}';
    }
}
