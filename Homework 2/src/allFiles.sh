echo THE ORDER OF LIST OF FILES:

files=()

if [ "$3" != "" ]
then
  cd $1

  for file in *.$3;
  do 
  if [ -f "../$2/$file" ]
    then files+=( "$file" )
  fi
  
  done
  
  cd ..
else
  cd $1

  for file in *.*;
  do 
  files+=( "$file [1]" )
  done
  
  cd ..
  cd $2
  
  for file in *.*;
  do
  files+=( "$file [2]" )
  done
  
  cd..
fi

printf '%s\n' "${files[@]}" | sort