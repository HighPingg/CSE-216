type bool_expr =
    | Lit of string
    | Not of bool_expr
    | And of bool_expr * bool_expr
    | Or of bool_expr * bool_expr;;

let rec solve literals values expression = match expression with

    | Lit literal -> if fst literals = literal
                        then fst values
                     else snd values 
    
    | Not boolEx -> not (solve literals values boolEx)

    | And (boolEx1, boolEx2) -> solve literals values boolEx1 &&
                                    solve literals values boolEx2

    | Or (boolEx1, boolEx2) -> solve literals values boolEx1 ||
                                    solve literals values boolEx2;;

let truth_table literal1 literal2 expression =
    [(true, true, solve (literal1, literal2) (true, true) expression);
    (true, false, solve (literal1, literal2) (true, false) expression);
    (false, true, solve (literal1, literal2) (false, true) expression);
    (false, false, solve (literal1, literal2) (false, false) expression)];;