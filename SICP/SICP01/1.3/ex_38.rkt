(load "ex_37.rkt")

(cont-frac (lambda (i) i) 
           (lambda (i) 
             (if (= (remainder i 3) 2) 
                 (/ (+ i 1) 1.5) 
                 1)) 
           10)
