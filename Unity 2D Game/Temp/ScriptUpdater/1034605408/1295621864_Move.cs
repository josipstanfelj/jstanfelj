using System;
using UnityEngine;
using System.Collections.Generic;
using System.Collections;
using static UnityEditor.Searcher.SearcherWindow.Alignment;

public class Move : MonoBehaviour
{
    public float moveSpeed = 5f;
    public Animator animator;
    public int groundLayer;
    public Transform groundCheck;
    public float groundCheckRadius = 0.1f;

    private Rigidbody2D rb;
    private bool isGrounded;
    float horizontal = 0f;

    public GameObject knifePrefab;
    public Transform knifeSpawnPoint;
    public float throwForce = 10f;


    private void ThrowKnife()
    {
        GameObject knife = Instantiate(knifePrefab, knifeSpawnPoint.position, Quaternion.identity);
        Rigidbody2D rb = knife.GetComponent<Rigidbody2D>();
        if (rb != null)
        {
            if (horizontal < 0)
            {
                rb.linearVelocity = new Vector2(-throwForce, 0);
                knife.transform.rotation = Quaternion.Euler(0, 0, 90);
            }
            else if (horizontal > 0)
            {
                rb.linearVelocity = new Vector2(throwForce, 0);
                knife.transform.rotation = Quaternion.Euler(0, 0, -90);
            }
            rb.linearVelocity = Vector2.up * throwForce;
        }
    }


    private void Start()
    {
        rb = GetComponent<Rigidbody2D>();
    }

    private void Update()
    {
  

        Jump();
        MoveCharacter();

        if (Input.GetMouseButtonDown(0))
        {
            ThrowKnife();
        }
    }

    private void MoveCharacter()
    {
        horizontal = Input.GetAxis("Horizontal");

        animator.SetFloat("MoveValue", Math.Abs(horizontal));

        if (horizontal < 0)
        {
            transform.localScale = new Vector3(-2, 1.4f, 1);
        }
        else if (horizontal > 0)
        {
            transform.localScale = new Vector3(2, 1.4f, 1);
        }
    }
    

    private void FixedUpdate()
    {
        Vector3 horizontalMove = new Vector3(horizontal, 0, 0);
        transform.position += horizontalMove * Time.fixedDeltaTime * moveSpeed;
    }

    private void Jump()
    {
        if (Input.GetButtonDown("Jump") && isGrounded)
        {
            rb.AddForce(new Vector2(0f, 5f), ForceMode2D.Impulse);
            animator.SetBool("Jump", true);
            
        }

    }
   

      private void OnCollisionEnter2D(Collision2D other)
      {
          if (other.gameObject.CompareTag("Ground"))
          {
              Vector3 normal = other.GetContact(0).normal;
              if (normal == Vector3.up)
              {
                  animator.SetBool("Jump", false);
                  isGrounded = true;
              }
          }
      }

      private void OnCollisionExit2D(Collision2D other)
      {
          if (other.gameObject.CompareTag("Ground"))
          {
              isGrounded = false;

          }
      }

}














/*
 * 
 * 
 * 
 * using System;
using UnityEngine;
public class Move : MonoBehaviour
{
    public float moveSpeed = 5f;
   
    private void Update()
    {
        Jump();
        float horizontal = Input.GetAxis("Horizontal");
        Vector3 movement = new Vector3(horizontal, 0f, 0f);
        transform.position += movement * Time.deltaTime * moveSpeed;
        // Postavljanje smjera prema horizontalnom kretanju
        gameObject.GetComponent<Animator>().SetFloat("MoveValue",
       Math.Abs(horizontal));
        if (horizontal < 0)
        {
            transform.localScale = new Vector3(-2, (float)1.4, 0); // Zrcali po X osi kad ide lijevo
        }
        else if (horizontal > 0)
        {
            transform.localScale = new Vector3(2, (float)1.4, 0); // Normalna skala kad ide desno
        }
    }



private void Jump()
    {
        if (Input.GetButtonDown("Jump"))
        {
            gameObject.GetComponent<Rigidbody2D>().AddForce(new
           Vector2(0f, 5f), ForceMode2D.Impulse);
        }
    }
}*/