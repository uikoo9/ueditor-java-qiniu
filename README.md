# ueditor-java-qiniu

## 说明
鉴于大部分用户对ueditor直接上传七牛云比较热衷，同时七牛云对sdk做了升级，

对原[ueditor-java-bcs-qiniu](https://github.com/uikoo9/ueditor-java-bcs-qiniu)进行升级，

1. 原ueditor-java-bcs-qiniu停止升级；
2. 停止对bcs（百度云存储）的支持；
3. 升级ueditor中上传到七牛云的代码，支持最新的七牛云sdk。 

## 简介
1. 对ueditor.jar源码进行修改
2. 使其支持上传文件，图片等到服务器，qiniu（七牛云）

## 使用
1. 请下载本项目并导入eclipse在tomcat下运行
2. config.properties中的jfinal.ueditor.upload_to
	1. 值为local时上传到服务器
	2. 值为qiniu时上传到qiniu
3. config.properties中的qiniu需要修改为自己的ak，sk，bucketname
4. WebRoot\ueditor-min-1.4.3\jsp\config.json中
	1. imageUrlPrefix，videoUrlPrefix，fileUrlPrefix，三个值需要修改
	2. 当上传到local时设置对应的local地址，例如：http://localhost/ue
	3. 当上传到qiniu时设置为对应的地址，例如：http://yourname.qiniudn.com/@

## jar包说明
1. 项目下共有11个包，依次说明；
2. [jfinal](http://www.jfinal.com/)相关jar，jfinal是一个mvc框架，类似ssh：
	1. jfinal-1.9-bin.jar
	2. freemarker-2.3.20.jar
3. ueditor-1.1.1相关jar:
	1. commons-codec-1.9.jar
	2. commons-fileupload-1.3.1.jar
	3. commons-io-2.4.jar
	4. json.jar
	5. ueditor-1.1.1-for-qiniu-new.jar
	6. 其中ueditor-1.1.1-for-qiniu-new.jar的源码进行过修改，详见下
4. qiniu-7.0.4相关jar
	1. gson-2.3.1.jar
	2. okhttp-2.3.0-SNAPSHOT.jar
	3. okio-1.3.0-SNAPSHOT.jar
	4. qiniu-java-sdk-7.0.4.jar

## 源码修改说明
1. 修改了com.baidu.ueditor.upload.StorageManager.java一个文件
2. 修改详情

	    private static State saveTmpFile(File tmpFile, String path) {
			State state = null;
			File targetFile = new File(path);
	
			if (targetFile.canWrite()) {
				return new BaseState(false, AppInfo.PERMISSION_DENIED);
			}
			
			String uploadto = QPropertiesUtil.get("jfinal.ueditor.upload_to");
			boolean uploaderror = false;
			if(QStringUtil.notEmpty(uploadto)){
				String key = "/" + path.split("//")[1];
				
				if("qiniu".equals(uploadto)){
					QQiNiuUtil.uploadFile(key, tmpFile.getAbsolutePath());
				}else{
					uploaderror = true;
				}
			}else{
				uploaderror = true;
			}
			
			if(uploaderror){
				try {
		            FileUtils.moveFile(tmpFile, targetFile);
		        } catch (IOException e) {
		            return new BaseState(false, AppInfo.IO_ERROR);
		        }
			}
			
			state = new BaseState(true);
			state.putInfo( "size", targetFile.length() );
			state.putInfo( "title", targetFile.getName() );
			
			return state;
		}

## 作者
1. uikoo9
2. 欢迎访问[uikoo9.com](http://uikoo9.com)