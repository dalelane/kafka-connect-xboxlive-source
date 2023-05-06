# kafka-connect-xboxlive-source

kafka-connect-xboxlive-source is a Kafka Connect source connector for getting events from Xbox LIVE into Kafka topics.

It produces events to the following topics, each with different types of Xbox LIVE activity.

**Topic: `ACHIEVEMENTS`**

Someone in your friends list on Xbox LIVE has earned an achievement.

| **field**        | **type** | **description**                                                                        | **example** |
| ---------------- | -------- | -------------------------------------------------------------------------------------- | ----------- |
| `date`           | String   | datetime when the achievement was earned                                               | 2022-12-19T01:16:32.147Z    |
| `gamertag`       | String   | username for who earned the achievement                                                | dalelane    |
| `gamername`      | String   | name of the user who earned the achievement, if they have a full name in their profile | Dale Lane   |
| `name`           | String   | name of the achievement                                                                | First Light |
| `description`    | String   | description of the achievement                                                         | Defeat God of Darknet |
| `icon`           | String   | URL for the icon for the achievement                                                   | http://images-eds.xboxlive.com/image?url=27S1DHqE.cHkmFg4nspsdy4wR_qt954iTFv8hr0Qk3Vhx5KcRHUYu.z25s6joKD5Iqo1DgOoa0QCM4UAdYDEfos_lfahQT0sPCa9PE4cYVY1SLqx6tm.a8hmkCgmIeuf |
| `contentname`    | String   | name of the game that the achievement was earned from                                  | Neon Abyss  |
| `contentimage`   | String   | URL of the image/graphic/cover-art for the game the achievement was earned from        | http://store-images.s-microsoft.com/image/apps.19663.13568911399767367.40f48bf2-3e0d-4d5e-82ae-91d8c14bd585.9cb6c1ed-b253-40ec-b2eb-6193fc894c41 |
| `platform`       | String   | platform that the game was played on                                                   | XboxOne     |
| `gamerscore`     | Integer  | points that the user earned from the achievement                                       | 20          |
| `rarityscore`    | Integer  | how rare it is for players to earn this achievement                                    | 20          |
| `raritycategory` | String   | how rare it is for players to earn this achievement                                    | Common      |


**Topic: `PRESENCE`**

One of your friends on Xbox LIVE has changed their status (e.g. started playing a game).

| **field**        | **type** | **description**                                                                        | **example** |
| ---------------- | -------- | -------------------------------------------------------------------------------------- | ----------- |
| `date`           | String   | datetime when the status changed                                                       | 2022-12-19T01:41:02.880418051Z |
| `userid`         | String   | unique id for the user whose status has changed                                        | 2533274819921149 |
| `state`          | String   | new status for the user                                                                | Online      |
| `titleid`        | String   | id of the game the user has started playing (will be null for Offline statuses)        | 1882004205  |
| `titlename`      | String   | name of the game the user has started playing (will be null for Offline statuses)      | Neon Abyss  |


**Topic: `USERPOSTS`**

One of your friends on Xbox LIVE has posted to the Xbox social network.

| **field**        | **type** | **description**                                                                        | **example** |
| ---------------- | -------- | -------------------------------------------------------------------------------------- | ----------- |
| `date`           | String   | datetime when the post was made                                                        | 2023-05-06T21:06:00Z |
| `gamertag`       | String   | username for who made the post                                                         | dalelane    |
| `gamername`      | String   | name of the user who made the post, if they have a full name in their profile          | Dale Lane   |
| `description`    | String   | contents of the post                                                                   | This is my post  |


**Topic: `TEXTPOSTS`**

One of your friends on Xbox LIVE has posted to the Xbox social network.

| **field**        | **type** | **description**                                                                        | **example** |
| ---------------- | -------- | -------------------------------------------------------------------------------------- | ----------- |
| `date`           | String   | datetime when the post was made                                                        | 2023-05-06T21:06:00Z |
| `gamertag`       | String   | username for who made the post                                                         | dalelane    |
| `gamername`      | String   | name of the user who made the post, if they have a full name in their profile          | Dale Lane   |
| `description`    | String   | contents of the post                                                                   | This is my post  |



## Building the connector

```sh
mvn package
```


## Configuration

Class name: `uk.co.dalelane.kafkaconnect.xboxlive.XblSourceConnector`

| Name                    | Description                                                              | Type    | Example                    |
| ----------------------- | ------------------------------------------------------------------------ | ------- | -------------------------- |
| `topic.prefix`          | Prefix for names of topics to create                                     | String  | XBOX.LIVE.                 |
| `xbl.api.key`           | API key for OpenXBL (https://xbl.io)                                     | String  |                            |
| `xbl.api.poll.interval` | How frequently the Connector should call the Xbox Live APIs (in seconds) | Integer | 600                        |

A sample configuration file called [`sample-connector.properties`](https://github.com/dalelane/kafka-connect-xboxlive-source/blob/master/sample-connector.properties) is included.


## Acknowledgements

This project is not in any way official or affiliated with Microsoft or Xbox. The connector gets data from Xbox using [OpenXBL](https://xbl.io/) - which is an unofficial API for getting data from Xbox LIVE.