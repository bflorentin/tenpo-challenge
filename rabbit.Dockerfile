FROM rabbitmq:management

ADD rabbit/rabbitmq.conf /etc/rabbitmq/
ADD rabbit/definitions.json /etc/rabbitmq/

RUN chown rabbitmq:rabbitmq /etc/rabbitmq/rabbitmq.conf /etc/rabbitmq/definitions.json