package com.example.hhb.remoteplayer;

import java.sql.*;
import java.util.ArrayList;

public class Mysql
{
    private String url= "";//JDBC的URL
    private String rootName = "";
    private String pwd ="";
    private String sqlstr="";
    private Connection conn= null;
    private Statement stmt  = null;
    private ResultSet rs=null;
    private ResultSetMetaData resultSetMetaData =null;

    //使用UnicodeBlock方法判断
    private static boolean isChineseByBlock(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            return true;
        } else {
            return false;
        }
    }

    public static int getStringLength(String str)
    {
        int count=0;
        for (int i = 0; i < str.length(); i++)
        {
            char t=str.charAt(i);
            if(isChineseByBlock(t))
                count+=2;
            else
                count++;
        }
        return count;
    }

    public static String getFormatString(String str)
    {
        StringBuilder builder=new StringBuilder(str);
        int l=getStringLength(str);
        for (int i = 0; i < 20-l; i++)
        {
            builder.append(' ');
        }
        return builder.toString();
    }

    private void connect() throws SQLException
    {
            conn = DriverManager.getConnection(url, rootName, pwd);
            stmt = conn.createStatement();//创建一个Statement对象
    }

    public Mysql(String ip,String rootName,String pwd) throws SQLException
    {
        this.url="jdbc:mysql://"+ip+"?useUnicode=true&characterEncoding=utf-8";
        this.rootName=rootName;
        this.pwd=pwd;
        connect();
    }

    public Mysql() throws SQLException
    {
        this("localhost:3306","root","");
    }

    public void execute(String SqlString) throws SQLException
    {

        sqlstr=SqlString;
        if(stmt.execute(sqlstr))
            rs=stmt.getResultSet();
        else
            rs=null;
    }

    public String[][] getResultSet() throws SQLException
    {
        if(rs==null)
            return new String[][]{{"No result to show\n"}};


        int ColumnCount=0;
        ArrayList<String[]> list=new ArrayList<>();

        resultSetMetaData = rs.getMetaData();
        String[] strarray=null;
        ColumnCount=resultSetMetaData.getColumnCount();
        strarray=new String[resultSetMetaData.getColumnCount()];
        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++)
        {
            strarray[i]=resultSetMetaData.getColumnName(i + 1); }list.add(strarray);
        while (rs.next())
        {
            strarray=new String[resultSetMetaData.getColumnCount()];
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++)
            {
                String tpye=resultSetMetaData.getColumnTypeName(i + 1);
                if (tpye.equals("INT"))
                {
                    strarray[i]=""+rs.getInt(i+1); } else if (tpye.equals("VARCHAR")) {
                    strarray[i]=""+rs.getString(i+1); } else if (tpye.equals("BIGINT")) {
                    strarray[i]=""+rs.getBigDecimal(i+1); } else if (tpye.equals("DATE")) {
                    strarray[i]=""+rs.getDate(i+1); } else if (tpye.equals("TINYINT")) {
                    strarray[i]=""+rs.getBoolean(i+1); } else if (tpye.equals("DOUBLE")) {
                    strarray[i]=""+rs.getDouble(i+1); } }list.add(strarray); }




        String[][] strs=new String[list.size()][ColumnCount];
        for (int i=0;i<strs.length;i++)
        {
            strs[i]=list.get(i);
        }
        return  strs;



    }

    public String[][] getResultSet(String SqlString) throws SQLException
    {
        execute(SqlString);
        return getResultSet();
    }

}