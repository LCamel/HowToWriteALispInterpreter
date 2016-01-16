How to Write a Lisp Interpreter

http://norvig.com/lispy.html

Slides: https://docs.google.com/presentation/d/1pMlTcUER1Kqll7XPZ5mBuMiYNGT8I04wIpre4fifmx0/edit?usp=sharing

(TWJUG 2016-01-16)

(define fib (lambda (n) (if (eq n 0) 1 (if (eq n 1) 1   (+ (fib (- n 1)) (fib (- n 2)))   ))))

(fib 5)


License: MIT

