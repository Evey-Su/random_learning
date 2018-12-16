package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//代表正在编辑的文档类
public class Document {
    //如果文档发生改变，changed会被设置为true
    private boolean changed = false;
    //一次需要保存的内容，可以理解为内容缓存
    private List<String> content = new ArrayList<>();

    private final FileWriter writer;

    //自动保存文档的线程
    private static AutoSaveThread autoSaveThread;

    public Document(String documentPath, String documentName) throws IOException {
        this.writer = new FileWriter(new File(documentPath, documentName));
    }

    //静态方法主要用于创建文档，顺便启动自动保存文档的线程
    public static Document create(String docmentPath, String documentName) throws IOException {
        Document document = new Document(docmentPath, documentName);
        autoSaveThread = new AutoSaveThread(document);
        autoSaveThread.start();
        return document;
    }

    //文件的编辑，其实就是往content中提交字符串
    public void edit(String content) {
        synchronized (this) {
            this.content.add(content);
            //文档改变，changed会变成true
            this.changed = true;
        }
    }

    //文档关闭的时候首先中断自动保存线程，然后关闭writer释放资源
    public void close() throws IOException {
        autoSaveThread.interrupt();
        writer.close();
    }

    //save方法用于外部显示进行文档保存
    public void save() throws IOException {
        synchronized (this) {
            //balking，如果文档已经被保存了，则直接返回
            if (!changed) {
                return;
            }
            System.out.println(Thread.currentThread() + " execute the save action");
            //将内容写入文档中
            for (String cacheLine : content) {
                this.writer.write(cacheLine);
                this.writer.write("\r\n");
            }
            this.writer.flush();
            //将changed修改为false,表明此刻再没有新的内容编辑
            this.changed = false;
            this.content.clear();
        }
    }


}
