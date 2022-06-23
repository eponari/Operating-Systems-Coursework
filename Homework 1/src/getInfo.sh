file="$1"

if [ -f "$file" ] ; then
  echo "File size of $file is: " $(stat --printf="%s" "$file") bytes
  echo "Permissions for $file: " $(ls -l $file)
elif [ -d "$file" ] ; then
  echo "Directory $file has the following files: " $(ls $file)
  echo "Permissions for each file in $file: " $(ls -l $file)
else
  touch "$file"
  date >> "$file"
fi