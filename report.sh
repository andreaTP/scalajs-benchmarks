#!/bin/sh

sbt -mem 8048 \
    'chameneosJS/clean' \
    'pingpongJS/clean' \
    'pipeJS/clean' \
    'skynetJS/clean' \
    'chameneosJS/fastOptJS' 'chameneosJS/fullOptJS' \
    'pingpongJS/fastOptJS' 'pingpongJS/fullOptJS' \
    'pipeJS/fastOptJS' 'pipeJS/fullOptJS' \
    'skynetJS/fastOptJS' 'skynetJS/fullOptJS'

echo "node fast"
echo "report" > nodeFast.log
node chameneos/.js/target/scala-2.12/chameneos-fastopt.js >> nodeFast.log
node pingpong/.js/target/scala-2.12/pingpong-fastopt.js >> nodeFast.log
node pipe/.js/target/scala-2.12/pipe-fastopt.js >> nodeFast.log
node skynet/.js/target/scala-2.12/skynet-fastopt.js >> nodeFast.log

echo "node full"
echo "report" > nodeFull.log
node chameneos/.js/target/scala-2.12/chameneos-opt.js >> nodeFull.log
node pingpong/.js/target/scala-2.12/pingpong-opt.js >> nodeFull.log
node pipe/.js/target/scala-2.12/pipe-opt.js >> nodeFull.log
node skynet/.js/target/scala-2.12/skynet-opt.js >> nodeFull.log

echo "graal fast"
echo "report" > graalFast.log
~/graalvm/bin/node chameneos/.js/target/scala-2.12/chameneos-fastopt.js >> graalFast.log
~/graalvm/bin/node pingpong/.js/target/scala-2.12/pingpong-fastopt.js >> graalFast.log
~/graalvm/bin/node pipe/.js/target/scala-2.12/pipe-fastopt.js >> graalFast.log
~/graalvm/bin/node skynet/.js/target/scala-2.12/skynet-fastopt.js >> graalFast.log

echo "graal full"
echo "report" > graalFull.log
~/graalvm/bin/node chameneos/.js/target/scala-2.12/chameneos-opt.js >> graalFull.log
~/graalvm/bin/node pingpong/.js/target/scala-2.12/pingpong-opt.js >> graalFull.log
~/graalvm/bin/node pipe/.js/target/scala-2.12/pipe-opt.js >> graalFull.log
~/graalvm/bin/node skynet/.js/target/scala-2.12/skynet-opt.js >> graalFull.log
