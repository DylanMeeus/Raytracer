Immutable Raytracer
== 

This is a raytracer implementation in Java that offers some basic raytracing features.
It deals with dielectrics, lambertian and metal materials with their own properties
for refraction and reflection of the rays that have been cast onto them.

The quirky thing is that each (core) class is entirely immutable. This is not common when
working with raytracers, because you want them to be speedy and performant. Hence
why I thought it'd be interesting to see how the performance holds up against
mutable C++ raytracers. 

