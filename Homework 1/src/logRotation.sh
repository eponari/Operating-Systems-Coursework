file="$1"

if [ -f "$file" ]
then
  size="$(stat --printf="%s" "$file")"
  if [ "$size" -lt $((1024*1024)) ]
  then
    newFile="$file|$(date).${file#*.}"
    touch "$newFile"
    cp "$file" "$newFile"  
  fi
fi