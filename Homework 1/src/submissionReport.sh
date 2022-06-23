n=5

if [ "$1" != "" ]
then n="$1"
fi

for file in ./* ; do
  if [ -d "$file" ]
  then
    studentId=${file:2}
    studentWork=""
    nrOfStudentWork=0

    for item in $(seq $n) ; do
      if [ -f "$studentId/hw$item.c" ]
      then
        nrOfStudentWork=$((nrOfStudentWork+1))
        studentWork="$studentWork hw$item.c "
      fi
    done

    if [ $nrOfStudentWork -gt 0 ]
    then echo "Student $studentId has submitted $nrOfStudentWork solution(s): $studentWork"
    fi
  fi
done