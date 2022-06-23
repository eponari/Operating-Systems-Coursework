directory=""
files=""

until [ "$1" == "" ]; do
  if [ -f "$1" ]
  then files="$files $1 "
  fi

  if [ -d "$1" ] 
  then directory="$directory $1 "
  fi

  shift
done

echo "The list of files: $files"
echo "The list of directories: $directory"