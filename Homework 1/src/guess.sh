number=$(($RANDOM%10 + 1))

guess=0
tries=0

until [ $guess -eq $number ]; do

  read -p "enter your guess: " guess
  tries=$((tries+1))

  if [ $guess -lt $number ]
  then
    echo "Your guess is less than the secret number."

  elif [ $guess -gt $number ]
  then
    echo "Your guess is greater than the secret number."

  else
    echo "You guessed it after $tries guesses."

  fi

done