#REMEMBER!!!!!: all vectors are interpreted as covectors if not transposed
#

## Service Functions
class LatticeBuchberger:
    
    @staticmethod
    def SolveOptimizationProblem(A,optimizationOrder,feasSol, debugMode=False):
        def PlusVector(v): 
            #i.e. v+

            v0=v[:]
            for i in range(0,len(v)):
                if(v0[i]<0):
                    v0[i]=0
            return v0

        def MinusVector(v): 
            #i.e. v-
            return PlusVector(-v)

        ## ORDERS

        #vector Lexicographic order
        def CompareVectorsLex(v,w): 
            #1 v>w, -1 v<w, 0 v=w

            if(len(v)!=len(w)):
                return None
            for i in range(0,len(v)):
                if(v[i]!=w[i]):
                    if(v[i]>w[i]):
                        return 1
                    else:
                        return -1
            return 0

        def CompareVectorsRevLex(v,w): 
            #1 v>w, -1 v<w, 0 v=w

            if(len(v)!=len(w)):
                return None
            for i in range(len(v)-1,-1,-1):
                if(v[i]!=w[i]):
                    if(v[i]>w[i]):
                        return 1
                    else:
                        return -1
            return 0

        def CompareVectorsDegLex(v,w,deg): 
            #1 v>w, -1 v<w, 0 v=w

            if(len(v)!=len(w)):
                return None
            if(v.dot_product(deg)>w.dot_product(deg)):
                return 1
            else:
                if(v.dot_product(deg)<w.dot_product(deg)):
                    return -1
                else:
                    return CompareVectorsLex(v,w)

        def CompareVectorsBlockDegLex(v,w,deg,block): 
            #1 v>w, -1 v<w, 0 v=w
            if(len(v)!=len(w)):
                return None
            
            #print("CompareBlock")
            #print(v[:block])
            #print(w[:block])
            
            ifBlock = CompareVectorsLex(v[:block],w[:block])
            if(ifBlock != 0):
                return ifBlock
            
            #print("CompareBlock2")
            #print(v[block:])
            #print(w[block:])
            
            if(v[block:].dot_product(deg)>w[block:].dot_product(deg)):
                return 1
            else:
                if(v[block:].dot_product(deg)<w[block:].dot_product(deg)):
                    return -1
                else:
                    return CompareVectorsLex(v[block:],w[block:])

        def CompareVectorsDegRevLex(v,w,deg): 
            #1 v>w, -1 v<w, 0 v=w
            if(len(v)!=len(w)):
                return None
            if(v.dot_product(deg)>w.dot_product(deg)):
                return 1
            else:
                if(v.dot_product(deg)<w.dot_product(deg)):
                    return -1
                else:
                    return CompareVectorsRevLex(v,w)

        #total vector order
        def CompareVectorsTotally(v,w): 
            #1 v>=w, 0 v=w, -1 v<=w, 2 ??

            if(len(v)!=len(w)):
                return None
            f=0

            for i in range(0,len(v)):
                if(v[i]!=w[i]):
                    if(v[i]>w[i]):
                        if(f==0):
                            f=1 
                        else:
                            if(f==-1):
                                return None #uncomparable

                    else:
                        if(f==0):
                            f=-1
                        else:
                            if(f==1):
                                return None #uncomparable
            return f

        ##Buchberger algorithm for Lattice (NOT TESTED YET)

        #REDUCTION

        def ReduceVectorBySet(f,B,order=['lex'],block=0):
            #Weismantel,Ziegler,Urbaniak notation
            #Ensure all b in B such that b > 0 w.r.to Lex order,
            #same should hold for f

            f0=f[:]
            wasReduction=True
            its=0
            while(wasReduction):
                wasReduction=False

                for g in B:
                    compRes=CompareVectorsTotally(PlusVector(g),PlusVector(f0))

                    if((compRes!=None) and (compRes<=0)):                         
                        if(order[0]=='deglex'):
                            compRes=CompareVectorsBlockDegLex(f0,g,order[1],block)
                              
                        if(compRes==1):                   
                            f0=f0-g
                        else:  
                            if(compRes==0):
                                return f0-g
                            f0=g-f0
                            
                        #print('RED')
                        #print(f0)
                        wasReduction=True
                        break

            #print("reductionResult", f0)
            return f0


        def ConstructSpairs(B,order,oldI,block=0):#it is possible to optimize
            #construct all possible Spairs

            Blist=list(B)
            #print(Blist)
            Spairs=[]
            for i in range(0,len(Blist)-1):
                
                if(i<=oldI):
                    begin=oldI+1
                else:
                    begin=i+1
                    
                for j in range(begin,len(Blist)):
                    
                    #disjoint initial support test
                    toForget=True
                    for k in range(0,len(Blist[i])):
                        if(Blist[i][k]>0):
                            if(Blist[j][k]>0):
                                toForget=False
                                break
                    if(toForget):
                        continue
                    #ENDdisjoint initial support test    
                    
                    if(order[0]=='deglex'):
                        if(CompareVectorsBlockDegLex(Blist[i],Blist[j],order[1],block)==1):
                            Spairs.append(Blist[i]-Blist[j])          
                        else: 
                            Spairs.append(Blist[j]-Blist[i])


            return Spairs


        def LatticeBuchberger(initialSet,order=['lex'],block=0): #Works but SpairsOptimization is needed
            #Finds Grobner Basis by implementing Buchberger algorithm
            #with respect to BlockDegLexOrder!!!!!!


            B=initialSet ##Be careful initialSet is not Set

            iterations=0
            wasReduction=True
            oldI=0
            while(wasReduction):
                #iterations=iterations+1
                #if(iterations>4):
                #    return
                print(len(B))          
                wasReduction=False
                
                Spairs=ConstructSpairs(B,order,oldI,block)
                oldI=len(B)-1

                for i in range(0,len(Spairs)):

                    rr=ReduceVectorBySet(Spairs[i],B,order,block)

                    if(CompareVectorsTotally(rr,zero_vector(QQ,len(rr)))!=0):
                        B.append(rr)
                        wasReduction=True  
                

            return B


        def MinimizeGrobnerBasis(grBasis,order=['lex']):
            #Minimizes the Grobner Basis

            inds=set(range(0,len(grBasis)))
            wasReduction=True

            for i in range(0,len(grBasis)):
                for j in inds:
                    if(i!=j):
                        if(CompareVectorsTotally(PlusVector(grBasis[i]),PlusVector(grBasis[j]))==1):
                            inds.remove(i)
                            break

            return [grBasis[i] for i in inds]

        def ReduceGrobnerBasis(grBasis):
            #Reduces the MINIMAL Grobner Basis
            inds=set(range(0,len(grBasis)))
            wasReduction=True

            for i in range(0,len(grBasis)):
                for j in inds:
                    if(i!=j):
                        mv=MinusVector(grBasis[i])
                        if(CompareVectorsTotally(mv,PlusVector(grBasis[j]))>=0):
                            grR=[grBasis[k] for k in range(0,len(grBasis)) if k!=i]
                            print(grR)
                            grBasis[i]=grBasis[i]+mv-ReduceVectorBySet(mv,grR)
                            break

            return [grBasis[i] for i in inds]


        ###
        
        
        def ReduceMonomialByBinomials(mon,B,optOrder):
            wasReduction=True
            checkin=0
            while(wasReduction):
                wasReduction=False
                for g in B:
                    if((CompareVectorsTotally(mon,g)>=0)and
                       (CompareVectorsBlockDegLex(zero_vector(len(mon)),g,optOrder[1],0)==-1)):
                        mon=mon-g

                        if(checkin %  10 ==0):
                            print(mon)

                        checkin=checkin+1
                        wasReduction=True
                        break
            return mon
        
        def FilterBasis(basis, block):
            newbasis = []
            for i in range(0,len(basis)):
                cont=False #add a vector to the basis
                for j in range(0,block):
                    if(basis[i][j]!=0):
                        cont=True# do not
                        break
                
                if(not cont):
                    #vec = []
                    
                    #for j in range(block,len(basis[i])):
                    #    vec.append(basis[i][j])
                        
                    newbasis.append(vector(basis[i][block:]))
                    
            return newbasis
        
        ##BEGIN OF SolveOptimizationProblem
        
        A=A.transpose()

        H, U =A.hermite_form(transformation=true)#HNF solution
        #H=H.transpose() #we need only the rank of it so it does not matter
        U=U.transpose()

        #A=A.transpose() #???

        AA=block_matrix([[zero_matrix(H.rank(),A.nrows()-H.rank())],[identity_matrix(A.nrows()-H.rank())]])

        rr=U*AA
        rr=rr.transpose()
        rr=rr.LLL()
        
        print(rr)
        
        vecs = []
        for i in range(0,rr.nrows()):
            vec = [0]
            vec.extend(rr.row(i))
            vec=vector(vec)
            #print(len(vec),len(order[1]))
            if(CompareVectorsBlockDegLex(vec,zero_vector(QQ,len(vec)),optimizationOrder[1],1)<0):
                vec=-vec
            vecs.append(vec)          
            
        vecs.append(vector([1]*(rr.ncols()+1)))
        
        #ordd=['deglex',vector([1,1,1,1, 4,3,2,1, 7,5,3,1, 10,7,4,1]) ]
        print("Buchberger.....")
        gB1=LatticeBuchberger(vecs,optimizationOrder,1)
        print(len(gB1))
        print("Minimize....")
        gB1=MinimizeGrobnerBasis(gB1)
        print(len(gB1))
        print("Filtering...")
        gB1=FilterBasis(gB1,1)
        print(len(gB1))
        
        #for i in range(0,len(gB1)):
            #if(CompareVectorsDegLex(gB1[i],zero_vector(len(gB1[i])),optimizationOrder[1],1)<0):
            #    gB1[i]=-gB1[i]
            #    print(gB1[i],i)
                
        opt=ReduceMonomialByBinomials(feasSol,gB1,optimizationOrder)
        print("opt",opt)
        