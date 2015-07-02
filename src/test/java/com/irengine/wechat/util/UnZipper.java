package com.irengine.wechat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnZipper {
	/**
	 * 解压文件到当前目录 功能相当于右键 选择解压
	 * 
	 * @param zipFile
	 * @param
	 * @author gabriel
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile) throws IOException {
		// 得到压缩文件所在目录
		String path = zipFile.getAbsolutePath();
		System.out.println(path);
		if (path.indexOf("/") > 0) {
			path = path.substring(0, path.lastIndexOf("/"));
		} else {
			path = path.substring(0, path.lastIndexOf("\\"));
		}
		ZipFile zip = new ZipFile(zipFile);
		String name = "" + System.currentTimeMillis();
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			zipEntryName = name
					+ zipEntryName.substring(zipEntryName.indexOf("/"));
			InputStream in = zip.getInputStream(entry);
			// outPath输出目录
			String outPath = path.substring(
					0,
					path.lastIndexOf("/") < 0 ? path.lastIndexOf("\\") : path
							.lastIndexOf("/"))
					+ "/WEB-INF/classes/views/" + zipEntryName;
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
	}

	public static void main(String[] args) {
		try {
			unZipFiles(new File("D:/test/uploaded/1.zip"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
