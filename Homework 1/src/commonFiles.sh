directory1="$1"
directory2="$2"

common=""

cd "$directory1"

if [ "$3" != "" ]
then
  for file in *.$3; do
    echo "$file"
    if [ -f "../$directory2/$file" ]
    then common="$common $file\n"
    fi
  done
else
  for file in *.*; do
    echo "$file"
    if [ -f "../$directory2/$file" ]
    then common="$common $file\n"
    fi
  done
fi

printf "THE LIST OF COMMON FILES: \n$common"