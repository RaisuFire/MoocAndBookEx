#lang planet neil/sicp
define (sqrt-iter guess oldguess x)
  (if (good-enough? guess oldguess)
      guess
      (sqrt-iter (improve guess x)
                 x)))

(define (improve guess x)
  (average guess (/ x guess)))                 

(define (average x y)
  (/ (+ x y) 2))

(define (good-enough? guess oldguess)
  (< (abs (- guess oldguess))
     (* guess 0.001)))

(define (square x) (* x x))

(define (sqrt x)
  (sqrt-iter 1.0 x))