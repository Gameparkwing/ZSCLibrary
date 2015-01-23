package org.waxberry.zsclibrary.data;

/**
 * Created by Waxberry on 2015/1/21.
 */
public class Book {

    public String mName;
    public String mAuthor;
    public String mIndexNum;
    public String mViewLoan;
    public String mSearchNote;
    public String mDetailLink;

    public Book(String name, String author, String indexNum, String viewLoan, String searchNote, String detailLink)
    {
        this.mName = name;
        this.mAuthor = author;
        this.mIndexNum = indexNum;
        this.mViewLoan = viewLoan;
        this.mSearchNote = searchNote;
        this.mDetailLink = detailLink;
    }

}
