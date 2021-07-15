package com.kayleh.result;

/**
 * 在成功和失败的时候用于结果返回表示
 * 另外它引入CodeMsg类，在CodeMsg类中，我们自定义了一些状态码和状态信息，用起来也比较方便
 *
 * @Author: Kayleh
 * @Date: 2020/12/3 16:37
 */
public class Result<T>
{
    //状态码
    private int code;
    //状态信息
    private String msg;
    //返回的数据
    private T data;

    public Result(T data)
    {
        this.data = data;
    }

    public static <T> Result<T> error(CodeMsg codeMsg)
    {
        return new Result<T>(codeMsg);
    }

    public static <T> Result<T> success(T data)
    {
        return new Result<T>(data);
    }

    public Result(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    private Result(CodeMsg codeMsg)
    {
        if (codeMsg != null)
        {
            msg = codeMsg.getMsg();
            code = codeMsg.getCode();
        }
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
}
