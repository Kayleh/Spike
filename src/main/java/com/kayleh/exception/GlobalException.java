package com.kayleh.exception;

import com.kayleh.result.CodeMsg;

/**
 * 定义程序序列化ID。
 * <p>
 * 序列化ID等同于身份验证，主要用于程序的版本控制，维护不同版本的兼容性以及避免在程序版本升级时程序报告的错误。
 * <p>
 * Java的序列化机制通过在运行时确定类的serialVersionUID来验证版本一致性。
 * <p>
 * 在反序列化期间，JVM将输入字节流中的serialVersionUID与相应本地实体（类）的serialVersionUID进行比较，如果相同就认为是一致的，能够反序列化，否则在序列化版本不一致的情况下会有例外。
 *
 * @Author: Kayleh
 * @Date: 2020/12/6 13:59
 */
public class GlobalException extends RuntimeException
{
    //private static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg)
    {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg()
    {
        return codeMsg;
    }
}
