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

let rec equivs equivFun list = 



(**Question 6*)

let rec goldbachpair num = 



(**Question 7*)





(**Question 8*)





(**Question 9*)





(**Question 10*)


