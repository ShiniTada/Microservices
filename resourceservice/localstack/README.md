# install

pip install localstack

# launch

docker-compose up

# install

pip install localstack

# Commands

https://gist.github.com/sats17/493d05d8d4dfd16b7dad399163075156

## List s3 buckets

aws --endpoint-url="http://localhost:4566" s3 ls

## List files in bucket

aws --endpoint-url="http://localhost:4566" s3 ls s3://music-bucket

# source

https://github.com/localstack/localstack