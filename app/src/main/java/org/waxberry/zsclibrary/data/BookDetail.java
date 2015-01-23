package org.waxberry.zsclibrary.data;

import java.util.List;

/**
 * Created by GJH-08 on 2015/1/23.
 */
public class BookDetail {

    public String mName;
    public String mAuthor;
    public String mISBN;
    public String mPress;
    public String mInformation;
    public String mTitle;
    public String mCategoryNum;
    public String mTheme;
    public String mVersion;
    public String mDate;
    public String mSearchNote;
    public List<StatusDetail> mStatusDetailList;

    public BookDetail(String name, String author, String isbn, String press, String information,
                      String title, String categoryNum, String theme, String version, String date,
                      String searchNote, List<StatusDetail> pList)
    {
        this.mName = name;
        this.mAuthor = author;
        this.mISBN = isbn;
        this.mPress = press;
        this.mInformation = information;
        this.mTitle = title;
        this.mCategoryNum = categoryNum;
        this.mTheme = theme;
        this.mVersion = version;
        this.mDate = date;
        this.mSearchNote = searchNote;
        this.mStatusDetailList = pList;
    }

    public class StatusDetail
    {
        public String mViewLoan;
        public String mIndexNum;
        public String mCode;
        public String mVolNum;
        public String mStatus;
        public String mType;

        public StatusDetail(String viewLoan, String indexNum, String code, String volNum, String status, String type)
        {
            this.mViewLoan = viewLoan;
            this.mIndexNum = indexNum;
            this.mCode = code;
            this.mVolNum = volNum;
            this.mStatus = status;
            this.mType = type;
        }
    }

}
