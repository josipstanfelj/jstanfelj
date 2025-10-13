using UnityEngine;

public class Collector : MonoBehaviour
{
    public float pickupRadius = 2f; // Radijus za pokupiti bundevu
    private bool isPlayerNearby = false; // Provjerava je li igraè u radijusu
    public GameObject porukaUI;
    public GameObject porukaTagType;
    public GameObject scoreTag;
    private static int score = 0;

    private void Start()
    {
        porukaUI.SetActive(false);
        porukaTagType.SetActive(false);
        scoreTag.SetActive(true);
    }

    public void ShowMessage(string tagType)
    {
        if (porukaUI != null)
        {
            porukaUI.SetActive(true);
            porukaTagType.SetActive(true);

            var tmpComponent = porukaTagType.GetComponent<TMPro.TMP_Text>();
            if (tmpComponent != null)
            {
                tmpComponent.text = tagType;
            }
        }
    }

    public void HideMessage()
    {
        if (porukaUI != null)
        {
            porukaUI.SetActive(false); // Sakrij poruku
        }

        if (porukaTagType != null)
        {
            porukaTagType.SetActive(false); // Sakrij specifiènu poruku

            // Resetiraj tekst unutar porukaTagType
            var tmpComponent = porukaTagType.GetComponent<TMPro.TMP_Text>();
            if (tmpComponent != null)
            {
                tmpComponent.text = ""; // Oèisti tekst
            }
        }
    }

    void Update()
    {
        if (isPlayerNearby && Input.GetKeyDown(KeyCode.E))
        {
            CollectItem();
        }
    }

    void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            isPlayerNearby = true;
            ShowMessage(gameObject.tag); // Prikaži poruku
        }
    }

    void OnTriggerExit2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            isPlayerNearby = false;
            HideMessage(); // Sakrij poruku
        }
    }

    void CollectItem()
    {
        HideMessage(); // Sakrij poruku
        Destroy(gameObject); // Uništi bundevu

        if(gameObject.tag == "Apple")
        {
            score++;
            var tmpComponent = scoreTag.GetComponent<TMPro.TMP_Text>();
            if (tmpComponent != null)
            {
                tmpComponent.text = "Score: " + score.ToString();
            }
        }

        if (gameObject.tag == "Pumpkin")
        {
            score = score + 2;
            var tmpComponent = scoreTag.GetComponent<TMPro.TMP_Text>();
            if (tmpComponent != null)
            {
                tmpComponent.text = "Score: " + score.ToString();
            }
        }

        if (gameObject.tag == "Mushrooms")
        {
            score = score + 1;
            var tmpComponent = scoreTag.GetComponent<TMPro.TMP_Text>();
            if (tmpComponent != null)
            {
                tmpComponent.text = "Score: " + score.ToString();
            }
        }

    }

    void OnDrawGizmos()
    {
        // Vizualizacija radijusa za pokupiti bundevu
        Gizmos.color = Color.yellow;
        Gizmos.DrawWireSphere(transform.position, pickupRadius);
    }
}

