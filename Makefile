#:::::NOTE:::::
#needs java 5 classpath, running without for now
JFLAGS = -Xlint -source 5 -target 5 -cp .:/usr/share/icedtea-web/plugin.jar
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	./syi/javascript/JSController.java \
	./syi/awt/TextCanvas.java \
	./syi/awt/Gui.java \
	./syi/awt/LTextField.java \
	./syi/awt/LButton.java \
	./syi/awt/Tab.java \
	./syi/awt/ImageCanvas.java \
	./syi/awt/HelpWindowContent.java \
	./syi/awt/LComponent.java \
	./syi/awt/Awt.java \
	./syi/awt/HelpWindow.java \
	./syi/awt/TextPanel.java \
	./syi/awt/MessageBox.java \
	./syi/util/ThreadPool.java \
	./syi/util/ByteInputStream.java \
	./syi/util/ClassLoaderCustom.java \
	./syi/util/VectorBin.java \
	./syi/util/Vector2.java \
	./syi/util/ByteStream.java \
	./syi/util/PProperties.java \
	./syi/util/Io.java \
	./syi/applet/AppletWatcher.java \
	./syi/applet/ServerStub.java \
	./paintchat_server/TextTalker.java \
	./paintchat_server/TextServer.java \
	./paintchat_server/XMLTextTalker.java \
	./paintchat_server/PchInputStream.java \
	./paintchat_server/ChatLogOutputStream.java \
	./paintchat_server/LineTalker.java \
	./paintchat_server/XMLTalker.java \
	./paintchat_server/TalkerInstance.java \
	./paintchat_server/Server.java \
	./paintchat_server/PaintChatTalker.java \
	./paintchat_server/PchOutputStreamForServer.java \
	./paintchat_server/TextTalkerListener.java \
	./paintchat_server/LineServer.java \
	./paintchat_server/PchOutputStream.java \
	./paintchat_frame/DataBeanInfo.java \
	./paintchat_frame/PFrame.java \
	./paintchat_frame/Console.java \
	./paintchat_frame/Data.java \
	./paintchat_frame/PopupMenuPaintChat.java \
	./paintchat_frame/FileManager.java \
	./paintchat_frame/ConfigDialog.java \
	./paintchat_client/TText.java \
	./paintchat_client/TLine.java \
	./paintchat_client/L.java \
	./paintchat_client/Mi.java \
	./paintchat_client/IMi.java \
	./paintchat_client/Data.java \
	./paintchat_client/Client.java \
	./paintchat_client/Me.java \
	./paintchat_client/DCF.java \
	./paintchat_client/Pl.java \
	./paintchat_http/HttpServer.java \
	./paintchat_http/TalkerHttp.java \
	./paintchat_http/HttpFiles.java \
	./paintchat/TT.java \
	./paintchat/TText.java \
	./paintchat/LO.java \
	./paintchat/normal/Tools.java \
	./paintchat/normal/ToolList.java \
	./paintchat/SW.java \
	./paintchat/TPic.java \
	./paintchat/admin/LocalAdmin.java \
	./paintchat/ToolBox.java \
	./paintchat/M.java \
	./paintchat/SRaster.java \
	./paintchat/pro/Tools.java \
	./paintchat/pro/TPalette.java \
	./paintchat/pro/TBar.java \
	./paintchat/pro/TPic.java \
	./paintchat/pro/TPen.java \
	./paintchat/Resource.java \
	./paintchat/Res.java \
	./paintchat/MgText.java \
	./paintchat/debug/Debug.java \
	./paintchat/debug/DebugListener.java \
	./paintchat/config/ConfigServer.java \
	./paintchat/config/ConfigApplet.java \
	./paintchat/config/Ao.java \
	./paintchat/config/PConfig.java \
	./paintchat/Config.java 

#default rule
default: pchat400_alpha.zip

pchat400_alpha.zip: pchat.jar PaintChat.jar
	find cnf | xargs zip -v pchat400_alpha.zip PaintChat.jar

PaintChat.jar: classes
	find . -iname \*\.class | xargs jar -cef paintchat_frame.PFrame PaintChat.jar

pchat.jar: classes
	cat pchat.list | xargs jar -cf pchat.jar
	cp pchat.jar ./cnf/template/pchat.jar

#because lazy
spchat.jar: pchat.jar
	jarsigner pchat.jar -signedjar spchat.jar paintchat

sign: spchat.jar
pchat: pchat.jar

classes: $(CLASSES:.java=.class)

clean:
	$(RM) `find paintchat* syi -name \*.class` PaintChat.jar pchat.jar pchat400_alpha.zip

