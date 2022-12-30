FROM cp.icr.io/cp/ibm-eventstreams-kafka:11.1.1

# For this repo, create the my-plugins directory and copy
#
# target/kafka-connect-xboxlive-source-0.0.2-jar-with-dependencies.jar
#
# into it before building the image. Copying all of the target directory
# in results in classloader problems with the dependencies.
COPY ./my-plugins/ /opt/kafka/plugins/

USER 1001
