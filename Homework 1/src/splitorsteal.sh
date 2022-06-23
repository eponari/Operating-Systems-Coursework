totalPrice="$1"

read -p "First contestant chose: " firstChoice
read -p "Second contestant chose: " secondChoice

if [ "$firstChoice" == "$secondChoice" ]
  then
  splitPrice=0
  if [ "$firstChoice" == "split" ]
    then
    splitPrice=$((totalPrice/2))
  fi  
  echo "The first contestant won $splitPrice and the second contestant won $splitPrice."
else
  price1="$totalPrice"
  price2="$totalPrice"

  if [ "$firstChoice" == "split" ]
  then
    price1=0
  else
    price2=0
  fi
  echo "The first contestant won $price1 and the second contestant won $price2."
fi