package org.waxberry.zsclibrary.data;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.waxberry.zsclibrary.utils.APIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Waxberry on 2015/1/21.
 */
public class DataRequester {

    private Context mContext;

    public DataRequester(Context context)
    {
        this.mContext = context;
    }


    private String getUUID(Context pContext)
    {
        String androidId = "" + Settings.Secure.getString(pContext.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        //Log.d("UUID", androidId);
        return androidId;
    }

    private String requestDataFromServerByHttpGet(String strURL)
    {
        // HttpGet对象
        HttpGet httpRequest = new HttpGet(strURL);
        String strResult = "";

        try
        {
            // HttpClient对象
            HttpClient httpClient = new DefaultHttpClient();
            // 获得HttpResponse对象
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 取得返回的数据
                strResult = EntityUtils.toString(httpResponse.getEntity());
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return strResult;
    }

    public List<Book> GetBookSearchResult(int searchType, String searchKey, int libType, int disSort,
                                          int disSortBy, int disYear)
    {
        String strResult = "";
        List<Book> mList = new ArrayList<Book>();

        String strURL = APIUtils.OPAC_BASE + "/sulcmis/index.php";
        strURL = strURL + "?searchtype1=" + searchType;         // 搜索类型。
        strURL = strURL + "&q1=" + searchKey;                    // 搜索关键字。
        strURL = strURL + "&Submit=检索";                       // 提交类型。
        strURL = strURL + "&controller=default";               // 书目检索。
        strURL = strURL + "&action=post";                       // 动作。
        strURL = strURL + "&libtype=" + libType;                // 数目类型（所有书刊，图书，期刊）。
        strURL = strURL + "&dissort=" + disSort;                // 排序根据。
        strURL = strURL + "&dissortby=" + disSortBy;            // 排序方式（升序，降序）。
        strURL = strURL + "&disyear=" + disYear;                // 出版年份。
        strURL = strURL + "&dispage=20";                        // 每页显示。
        strURL = strURL + "&disCD=all";                         // 光盘。

        strResult = requestDataFromServerByHttpGet(strURL);

        if(strResult.equals(""))
        {
            return mList;
        }
        int size;
        String name, author, indexNum, viewLoan, searchNote, detailLink;
        Document doc = Jsoup.parse(strResult);
        Element content;
        Elements row;
        content = doc.body();
        content = content.getElementsByClass("list").first();
        content = content.getElementsByTag("table").get(1);
        row = content.getElementsByTag("tr");

        size = row.size();
        for (int i = 0; i < size; i+=3)
        {
            // 获取第一个td片段。
            content = row.get(i).getElementsByTag("td").first();
            name = content.getElementsByClass("basic").first().text();
            detailLink = content.getElementsByTag("a").first().attr("href");
            indexNum = content.getElementsByClass("indexnum").first()
                    .getElementsByTag("strong").first().text();
            Pattern p = Pattern.compile("</span>([\\s\\S]*?)</td>");
            Matcher m = p.matcher(content.toString());
            author = "";
            if(m.find())
            {
                author  = m.group(1);
            }
            // 获取第二个td片段。
            content = row.get(i + 1).getElementsByTag("td").first();
            viewLoan = content.getElementsByClass("viewloan").first().text();
            // 获取第三个td片段。
            content = row.get(i + 2).getElementsByTag("td").first();
            searchNote = content.getElementsByClass("searchnote").first().html().replace("<br>", "\n");

            Book book = new Book(name, author, indexNum, viewLoan, searchNote, detailLink);
            mList.add(book);
        }

        Log.d("name", mList.get(0).mName);
        Log.d("author", mList.get(0).mAuthor);
        Log.d("indexNum", mList.get(0).mIndexNum);
        Log.d("viewLoan", mList.get(0).mViewLoan);
        Log.d("searchNote", mList.get(0).mSearchNote);
        Log.d("detailLink", mList.get(0).mDetailLink);

        return mList;
    }

    public List<BookDetail> GetBookSearchResult(String strURL)
    {
        String strResult = "";
        List<BookDetail> mList = new ArrayList<BookDetail>();

        strResult = requestDataFromServerByHttpGet(strURL);

        if(strResult.equals(""))
        {
            return mList;
        }

        int table_size, row_size;
        String name, author, isbn, press, information, title, categoryNum, theme, version ,
                date, indexNum, viewLoan, searchNote, code, volNum, status, type;

        Document doc = Jsoup.parse(strResult);
        Element content, status_content;
        Elements tables, row;
        content = doc.body();
        content = content.getElementsByClass("viewbody").first();
        tables = content.getElementsByTag("table");

        row = tables.get(0).getElementsByTag("tr");
        row_size = row.size();
        for (int i = 0; i < row_size; i++)
        {
            Log.d("tr", row.html());
        }

        return mList;
    }
}
