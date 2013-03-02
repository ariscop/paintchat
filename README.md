paintchat
=========

A further improved version of the ancient paintchat program.

Changes:
- All warnings removed! acording to eclipse this is an impecable codebase
- Re-encoded all the japanese in utf-8, no more broken ?'s (was in shift_jis)
- Translated the ui, with google translate. better than nothing
- Minor bugfixes
- Minor performance improvments (removed a few threads :D)
- Fixed the undocumented Connection_Host property, so the client and server
  can be on different domains
- Added 'ExceptionHandler' which will show an anoying popup when exceptions
  happen. will remove that eventually.

also extended the makefile, it properly packages everything into a zip similar
to the original release (pchat400_alpha.zi)

Current working build is here
https://www.ariscop.net/files/pchat400_alpha.zip

to build yourself requires make, javac, zip and prefferably a unix environment.
mingw should be suitable for windows users.
