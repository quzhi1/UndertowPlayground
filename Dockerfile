FROM centos:7
RUN set -xe \
	# install general packages
	; yum -y install \
		# jdk version and alpn jar version go 1:1, changing jdk version without changing alpn jar would break our project. so think before you do anything
		# this is your friend https://www.eclipse.org/jetty/documentation/9.4.x/alpn-chapter.html#alpn-versions
		java-1.8.0-openjdk-1.8.0.191.b12-0.el7_5.x86_64 \
		net-tools \
		openssl \
		gettext \
		sudo \
  ; yum clean all

COPY ./target/*-with-dependencies.jar /jars/service-jar.jar
CMD java -cp /jars/service-jar.jar com.quzhi1.undertowplayground.Main