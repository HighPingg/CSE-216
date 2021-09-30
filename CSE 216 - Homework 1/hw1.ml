(**Question 1*)

let rec pow x n = 
    
    if n == 0 then 1
    else x * pow x (n - 1);;

let rec float_pow x n = 

    if n == 0 then 1.
    else x *. float_pow x (n - 1);;

(**Question 2*)

let rec compress list = match list with

    | x1 :: x2 :: xs -> if x1 = x2
                        then compress (x2 :: xs)
                        else x1 :: compress (x2 :: xs)

    | x -> x;;

(**Question 3*)

let rec remove_if list pred = match list with

    | [] -> []

    | x :: xs -> if pred x
                 then remove_if xs pred
                 else x :: remove_if xs pred;;

(**Question 4*)

let rec slice list i j = match list with

    | [] -> []

    | x :: xs -> if i <= 0 && j > 0
                 then x :: slice xs (i - 1) (j - 1)
                 else slice xs (i - 1) (j - 1);;

(**Question 5*)

(**Get the element at index from list*)

let rec equivs func list =
    let rec equivsHelper equiv nEquiv list comp = match list with

        | [] -> equiv :: equivs func nEquiv 
        | x :: xs -> if func comp x
                     then equivsHelper (x :: equiv) nEquiv xs comp
                     else equivsHelper equiv (x :: nEquiv) xs comp
    
  in match list with 
    
        | [] -> []
        | x :: xs -> equivsHelper [x] [] xs x;; 

(**Question 6*)

(** isPrime calls isPrimeHelper with num is the number we
  * want to find primeness and x is the number we're checking
  * it against. x must be initialized at 2 and we will 
  * check up to x.
  *)

let isPrime num =
    let rec isPrimeHelper num x =

        if (num mod x = 0)         then false
        else if (x = num - 1)       then true
        else                        isPrimeHelper num (x + 1)
        
    in 
    
    if num < 2          then false
    else if num = 2     then true
    else                isPrimeHelper num 2;;


let goldbachpair num =
    let rec goldbachpairLooper x = 

        if (isPrime x && isPrime (num - x))
        then (x, num - x)
        else goldbachpairLooper (x + 1)

    in goldbachpairLooper 2;;

(**Question 7*)

let rec equiv_on f g lst = match lst with

    | [] -> true

    | x :: xs -> if f x <> g x
                 then false
                 else equiv_on f g xs;;

(**Question 8*)

let rec pairwisefilter cmp lst = match lst with

    | [] -> []
    | [x] -> [x]

    | x1 :: x2 :: xs -> (cmp x1 x2) :: pairwisefilter cmp xs;;

(**Question 9*)

let rec polynomial list x = match list with

    | [] -> 0

    | x1 :: xs -> (fst x1) * (pow x (snd x1))
                            + polynomial xs x;;

(**Question 10*)

let rec powersetRecur func list = match list with

    | [] -> []

    | x :: xs -> func x :: powersetRecur func xs;;

let rec powerset list = match list with

    | [] -> [[]]

    | x :: xs -> powerset xs @
                
                powersetRecur (fun y -> x :: y) (powerset xs);;