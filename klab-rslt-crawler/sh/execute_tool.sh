#!/bin/bash

## 環境変数を設定する
readonly HOME_DIR=`dirname $(pwd)`
readonly LIB_DIR=$HOME_DIR/lib
readonly JAR_FILE=$LIB_DIR/klab-rslt-crawler-0.1.0-all.jar
readonly LOG_DIR=$HOME_DIR/log
readonly LOG_FILE=$LOG_DIR/application_`date +%Y%m%d_%H%M%S`.log

## ログディレクトリを作成する
if [ ! -d $LOG_DIR ]; then
	mkdir $LOG_DIR
fi

## ツールを実行する
java -jar $JAR_FILE &>>$LOG_FILE
ret=$?
read -p "処理が終了しました。（終了コード: $ret）"
exit $ret
