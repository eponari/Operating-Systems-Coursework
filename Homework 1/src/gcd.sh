nr1="$1"
nr2="$2"

if [ $nr1 -ge $nr2 ]
  then
  temp="$nr1"
  nr1="$nr2"
  nr2="$temp"
fi

while [ $((nr1%nr2)) != 0 ]; do
  temp=$((nr1%nr2))
  nr1="$nr2"
  nr2="$temp"
done

echo "$nr2"