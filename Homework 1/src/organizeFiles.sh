for file in *.* ; do
  if [ "./$file" == $0 ]
  then  continue
  elif [ ! -d "${file#*.}" ]
  then mkdir ${file#*.}
  fi
  
  mv "$file" ${file#*.}
done

echo "Finished organizing files"

totalSize=$(du -sb --apparent-size . | cut -f1)

for file in ./* ; do
  folderSize=$(du -sb --apparent-size "./$file" | cut -f1)
  percentage=$(((100*folderSize)/totalSize))
  printf "$file ocuppy $percentage %%\n"
done