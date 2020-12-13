package com.kayleh.result;

/**
 * code用来保存状态码，msg状态信息，data是返回的对象（泛型）
 *
 * @Author: Kayleh
 * @Date: 2020/12/3 16:37
 */
public class Result<T>
{
    private int code;
    private String msg;
    private T data;

    public Result(T data)
    {
        this.data = data;
    }

    public static <T> Result<T> error(CodeMsg data)
    {
        return new Result<T>(data);
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
