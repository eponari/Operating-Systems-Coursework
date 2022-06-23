directory="$1"
n="$2"

if [ ! -d "$directory" ] 
then mkdir "$directory"
fi

for file in *.*; do
  touch "$directory/$file"

  if [ $(wc -l < $file) -lt $n ] 
  then cp "$file" "$directory/$file"
  else
  head -n $n $file >> "$directory/$file"
  fi
done