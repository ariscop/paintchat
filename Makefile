#:::::NOTE:::::
JFLAGS = -Xlint
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

SOURCES = $(shell cat files.list)
CLASSES = $(SOURCES:.java=.class)
PCHAT   = $(shell cat pchat.list)

#default rule
default: pchat400_alpha.zip

pchat400_alpha.zip: pchat.jar PaintChat.jar $(shell find cnf)
	find cnf | xargs zip -v pchat400_alpha.zip PaintChat.jar

PaintChat.jar: $(CLASSES)
	jar -cef paintchat_frame.PFrame PaintChat.jar $(CLASSES)

res.zip: $(shell find res)
	cd res && find | xargs zip -v ../res.zip

tt_def.zip: $(shell find tt)
	find tt | xargs zip -v tt_def.zip

pchat.jar: $(PCHAT) res.zip tt_def.zip
	jar -cf pchat.jar $(PCHAT)
	cp pchat.jar ./cnf/template/pchat.jar

clean:
	$(RM) $(CLASSES) \
	       PaintChat.jar \
	       pchat.jar \
	       pchat400_alpha.zip \
	       res.zip \
	       tt_def.zip

